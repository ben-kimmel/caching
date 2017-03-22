package runner;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import cache.ICacheStep;
import cache.ICacheWrapper;
import cache.internal.ICacheInternal;
import cache.logging.writers.ILogWriter;

public class ConfigurableJSONDriver {

	public static void main(String[] args) {
		File config = new File("configurations/default.json");
		if (args.length > 0) {
			config = new File(args[0]);
		}
		System.out.println(config.exists());
		JSONParser parser = new JSONParser();
		try {
			JSONArray runnerConfigs = (JSONArray) parser.parse(new FileReader(config));
			for (Object runnerConfig : runnerConfigs) {
				runITestRunner((JSONObject) runnerConfig);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void runITestRunner(JSONObject runnerConfig)
			throws SecurityException, IllegalArgumentException, ReflectiveOperationException {
		int runs = ((Long) runnerConfig.get("Runs")).intValue();
		ITestRunner runner = generateITestRunner(runnerConfig);
		runner.run(runs);
	}

	private static ITestRunner generateITestRunner(JSONObject rc)
			throws SecurityException, IllegalArgumentException, ReflectiveOperationException {
		ITestRunner runner = new DefaultTestRunner();
		if (rc.get("TestRunner") != null) {
			runner = construct(ITestRunner.class, (String) rc.get("TestRunner"), (JSONArray) rc.get("TestRunnerArgs"));
		}

		ICacheWrapper cacheWrapper = generateICacheWrapper(rc.get("CacheWrapper"), rc.get("CacheWrapperArgs"));
		for (ICacheStep cacheStep : generateCacheSteps(rc.get("CacheSteps"))) {
			cacheWrapper.provideCacheStep(cacheStep);
		}
		cacheWrapper.provideCacheImplementation(
				generateICacheInternal(rc.get("CacheInternal"), rc.get("CacheInternalArgs")));
		runner.provideCacheWrapper(cacheWrapper);

		runner.provideEnumerator(
				generateRequestEnumerator(rc.get("RequestEnumerator"), rc.get("RequestEnumeratorArgs")));

		for (ILogWriter logWriter : generateLogWriters(rc.get("LogWriters"))) {
			runner.provideLogWriter(logWriter);
		}

		return runner;
	}

	private static List<ILogWriter> generateLogWriters(Object logWriters) throws ReflectiveOperationException {
		List<ILogWriter> lws = new ArrayList<ILogWriter>();
		for (Object o : (JSONArray) logWriters) {
			JSONObject jo = (JSONObject) o;
			// TODO: fill this out
			lws.add(construct(ILogWriter.class, (String) jo.get("Writer"), (JSONArray) jo.get("WriterArgs")));
		}
		return lws;
	}

	@SuppressWarnings("unchecked")
	private static Enumeration<? extends Integer> generateRequestEnumerator(Object requestEnumerator, Object args)
			throws ReflectiveOperationException {
		return construct(Enumeration.class, (String) requestEnumerator, (JSONArray) args);
	}

	private static List<ICacheStep> generateCacheSteps(Object cacheSteps) throws ReflectiveOperationException {
		List<ICacheStep> cs = new ArrayList<ICacheStep>();
		for (Object o : (JSONArray) cacheSteps) {
			JSONObject jo = (JSONObject) o;
			cs.add(construct(ICacheStep.class, (String) jo.get("Step"), (JSONArray) jo.get("StepArgs")));
		}
		return cs;
	}

	private static ICacheInternal generateICacheInternal(Object cacheInternal, Object args)
			throws ReflectiveOperationException {
		return construct(ICacheInternal.class, (String) cacheInternal, (JSONArray) args);
	}

	private static ICacheWrapper generateICacheWrapper(Object cacheWrapper, Object args)
			throws ReflectiveOperationException {
		return construct(ICacheWrapper.class, (String) cacheWrapper, (JSONArray) args);
	}

	@SuppressWarnings("unchecked")
	private static <T> T construct(Class<T> returnType, String className, JSONArray argArray)
			throws ReflectiveOperationException {
		ArrayList<Object> args = new ArrayList<Object>();
		ArrayList<Class<?>> argClasses = new ArrayList<Class<?>>();
		for (Object o : (JSONArray) argArray) {
			args.add(o);
			argClasses.add(o.getClass());
		}
		System.out.println("CLASSNAME: " + className);
		System.out.println("ARGS: " + args.toString());
		System.out.println("ARG TYPES: " + argClasses.toString());
		System.out.println("CLASS: " + returnType.getCanonicalName());
		System.out.println();
		returnType = (Class<T>) Class.forName(className);
		Constructor<T> classConstructor = returnType.getConstructor((Class<?>[]) argClasses.toArray());
		return classConstructor.newInstance(args.toArray());
	}
}
