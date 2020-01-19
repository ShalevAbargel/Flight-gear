package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import server_side.MySerialServer;

public class pathCalculator {

	int[][] matrix;
	int startX; 
	int startY; 
	int destX; 
	int destY;
	
	public pathCalculator(int[][] matrix, int startX, int startY, int destX, int destY) {
		super();
		this.matrix = matrix;
		this.startX = startX;
		this.startY = startY;
		this.destX = destX;
		this.destY = destY;
	}

	public String calc(model model,PrintWriter out,BufferedReader in) throws IOException {
		synchronized (MySerialServer.lock) {
			MySerialServer.lock.notify();
		}	
		int i, j;
		for (i = 0; i < matrix.length; i++) {
			for (j = 0; j < matrix[i].length - 1; j++) {
				out.print(matrix[i][j] + ",");
			}
			out.println(matrix[i][j]);
		}
		out.println("end");
		out.println(startX + "," + startY);
		out.println(destY + "," + destX);
		out.flush();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String sol;
		sol = in.readLine();
		return sol;
	}
}
