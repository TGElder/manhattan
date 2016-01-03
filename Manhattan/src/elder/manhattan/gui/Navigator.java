package elder.manhattan.gui;

import org.lwjgl.input.Mouse;

import elder.geometry.Point;
import elder.manhattan.graphics.CityDrawer;

public class Navigator implements MouseListener
{

	private final CityDrawer cityDrawer;
	
	public Navigator(CityDrawer cityDrawer)
	{
		this.cityDrawer = cityDrawer;
		cityDrawer.addMouseListener(this);
	}
	
	@Override
	public void onMove(Point point)
	{
		
	}

	@Override
	public void onLeftClick(Point point)
	{
		
	}

	@Override
	public void onMiddleClick(Point point)
	{
		
	}

	@Override
	public void onRightClick(Point point)
	{
		
	}

	@Override
	public void onLeftDrag(Point offset)
	{
		cityDrawer.translate(offset.x/cityDrawer.getZoom(),offset.y/cityDrawer.getZoom());
	}

	@Override
	public void onMiddleDrag(Point offset)
	{
		
	}

	@Override
	public void onRightDrag(Point offset)
	{
		
	}

	@Override
	public void onWheelUp()
	{
		cityDrawer.adjustZoom(2,Mouse.getEventX(),Mouse.getEventY());
	}

	@Override
	public void onWheelDown()
	{
		cityDrawer.adjustZoom(0.5,Mouse.getEventX(),Mouse.getEventY());
	}

}
