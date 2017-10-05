package algvis.ds.dynamicarray;

import algvis.core.Settings;
import algvis.ui.NewVisPanel;

public class DynamicArrayPanel extends NewVisPanel{
  private static final long serialVersionUID = -4098377740442930253L;

  public DynamicArrayPanel(Settings S) {
    super(S);
  }
  @Override
  protected void initDS() {
    D = new DynamicArray(this);
    scene.add(D);
    buttons = new DynamicArrayButtons(this);
  }
}
