package com.fanteng.core.logback;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class LogbackClassicConverter extends ClassicConverter {

	@Override
	public String convert(ILoggingEvent event) {
		RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
		String name = runtime.getName();
		return name.substring(0, name.indexOf("@"));
	}

}
