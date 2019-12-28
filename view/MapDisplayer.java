package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class MapDisplayer extends Canvas{
	public static int[][] mapData;
	double max,min;
	
    public void setMapData(int[][] mapData) {
        this.mapData = mapData;
        this.min = Double.MAX_VALUE;
        this.max =0;
        for(int i=0;i<mapData.length;i++)
            for (int j=0;j<mapData[i].length;j++)
            {
                if(min>mapData[i][j])
                    min=mapData[i][j];
                if(max<mapData[i][j])
                    max=mapData[i][j];
            }
        redraw();
    }
	
    
	public void redraw() {
		if( mapData != null)
		{
			double W = this.getWidth();
			double H = this.getHeight();
			double w = W / mapData[0].length;
			double h = H / mapData.length;
			GraphicsContext gc = getGraphicsContext2D();
			double fifth = (max - min)/5;
			for(int i =0; i < mapData.length; i++) {
				for(int j=0; j< mapData[i].length;j++)
				{
					if(mapData[i][j] >= min && mapData[i][j] < fifth)
					{
		                gc.setFill(javafx.scene.paint.Color.rgb(255,0,0));
	                    gc.fillRect(j*w,i*h,w,h);
					}
					else if(mapData[i][j] >= fifth && mapData[i][j] < fifth*2)
					{
		                gc.setFill(javafx.scene.paint.Color.rgb(250,128,114));
	                    gc.fillRect(j*w,i*h,w,h);
					}
					else if(mapData[i][j] >= fifth*2 && mapData[i][j] < fifth*3)
					{
		                gc.setFill(javafx.scene.paint.Color.rgb(240,230,140));
	                    gc.fillRect(j*w,i*h,w,h);
					}
					else if(mapData[i][j] >= fifth*3 && mapData[i][j] < fifth*4)
					{
		                gc.setFill(javafx.scene.paint.Color.rgb(152,251,152));
	                    gc.fillRect(j*w,i*h,w,h);
					}
					else {
		                gc.setFill(javafx.scene.paint.Color.rgb(0,128,0));
	                    gc.fillRect(j*w,i*h,w,h);
					}
				}
			}
		}
	}
}
