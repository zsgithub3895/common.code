package com.zs.algorithm;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
/**
 * 	写一个生产者-消费者队列
	可以通过阻塞队列实现,也可以通过wait-notify来实现.
	使用阻塞队列来实现
 * */
//生产者
public class Producer implements Runnable {
	private final BlockingQueue<Integer> queue;

	public Producer(BlockingQueue q) {
		this.queue = q;
	}

	@Override
	public void run() {
		try {
			while (true) {
				Thread.sleep(1000);// 模拟耗时
				queue.put(produce());
			}
		} catch (InterruptedException e) {

		}
	}

	private int produce() {
		int n = new Random().nextInt(10000);
		System.out.println("Thread:" + Thread.currentThread().getId() + " produce:" + n);
		return n;
	}
}
