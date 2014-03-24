package com.isoft.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 工作线程
 */
public abstract class WorkThread implements Runnable {

	public static int RUNNING = 1;
	public static int WAITING = 0;

	private volatile boolean terminated = false;

	private boolean actived = false;
	private volatile boolean paused = false;

	protected AtomicInteger state = new AtomicInteger(WAITING);

	protected long id;

	public long getId() {
		return id;
	}

	public WorkThread() {
		Thread t = new Thread(this);
		t.start();
		id = t.getId();
	}

	protected abstract int doWork() throws Exception;
	
	protected void beforeDoWork() {
	}

	protected void afterDoWork() {
	}

	public final void run() {
		try {
			while (!terminated) {
				synchronized (this) {
					if (!actived) {
						this.wait();
					}
					actived = false;
				}
				state.set(RUNNING);
				beforeDoWork();
				while (!paused && (doWork() > 0));
				afterDoWork();
				state.set(WAITING);
			}
		} catch (Exception e) {
			// 退出线程
			e.printStackTrace();
		}
	}

	public synchronized void pause() {
		paused = true;
	}

	public void active() {
		this.paused = false;
		synchronized (this) {
			this.actived = true;
			this.notifyAll();
		}
	}

	public void terminate() {
		paused = true;
		terminated = true;
		synchronized (this) {
			this.notifyAll();
		}
	}

	public int getState() {
		return state.get();
	}
}
