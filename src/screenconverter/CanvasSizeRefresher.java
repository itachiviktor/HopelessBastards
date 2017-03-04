package screenconverter;

import graphicsEngine.IRenderer;

public class CanvasSizeRefresher implements ICanvasSizeRefresher{
	/*Ez egy olyan komponens, ami a Canvas aktu�lis sz�less�g�t �s magass�g�t tudja biztos�tani.*/
	private IRenderer renderer;
	
	public CanvasSizeRefresher(IRenderer renderer) {
		this.renderer = renderer;
	}
	
	@Override
	public void refresh(Size canvasSize) {
		canvasSize = renderer.getCanvasSize();		
	}
}