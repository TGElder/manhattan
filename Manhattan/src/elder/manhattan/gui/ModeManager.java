package elder.manhattan.gui;


import elder.geometry.Point;

public class ModeManager implements MouseListener
{
	
	private Mode mode;
	
	@Override
	public void onMove(Point cityPoint)
	{
		mode.onMove(cityPoint);
	}

	@Override
	public void onLeftClick(Point cityPoint)
	{
		mode.onLeftClick(cityPoint);
	}

	@Override
	public void onMiddleClick(Point cityPoint)
	{
		mode.onMiddleClick(cityPoint);
	}

	@Override
	public void onRightClick(Point cityPoint)
	{
		mode.onRightClick(cityPoint);
	}

	@Override
	public void onLeftDrag(Point screenOffset)
	{
		mode.onLeftDrag(screenOffset);
	}

	@Override
	public void onMiddleDrag(Point screenOffset)
	{
		mode.onMiddleDrag(screenOffset);
	}

	@Override
	public void onRightDrag(Point screenOffset)
	{
		mode.onRightDrag(screenOffset);
	}

	@Override
	public void onWheelUp()
	{
		mode.onWheelUp();
	}

	@Override
	public void onWheelDown()
	{
		mode.onWheelDown();
	}

	public Mode getMode()
	{
		return mode;
	}
	
	public void setMode(Mode mode)
	{
		if (this.mode!=null)
		{
			this.mode.disable();
		}
		this.mode = mode;
		if (mode!=null)
		{
			mode.enable();
		}
	}

}
