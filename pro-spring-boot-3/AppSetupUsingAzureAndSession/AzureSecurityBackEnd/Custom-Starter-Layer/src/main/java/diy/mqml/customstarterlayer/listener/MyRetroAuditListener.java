package diy.mqml.customstarterlayer.listener;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

/**
 *
 * 1. Purpose
 * The class listens for ApplicationReadyEvent, which is published once the Spring application is fully started and ready to serve requests.
 *
 * 2. Behavior
 * When the event is fired, the onApplicationEvent method is invoked, and a message ("MyRetroListener: ApplicationReadyEvent received") is printed to the console.
 *
 * */
public class MyRetroAuditListener implements ApplicationListener<ApplicationReadyEvent> {

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		System.out.println("MyRetroListener: ApplicationReadyEvent received");
	}
}
