package com.stevezero.game.controls.menu;

import java.util.ArrayList;
import java.util.List;

import com.stevezero.game.assets.font.Font;
import com.stevezero.game.controls.events.EventType;
import com.stevezero.game.controls.onscreen.Interactable;
import com.stevezero.game.controls.onscreen.MenuButton;
import com.stevezero.game.engine.graphics.rendering.Renderable;

/**
 * Helper class for controlling a set of options which can be selected.
 * Each option has an id for keyboard controls.
 * Each option also emits a specific event set for mouse controls.
 */
public class Options {
  private static final int NONE = -1;
  private final List<MenuButton> menuList;
  private final List<Interactable> interactableList;
  private int selectedId;
    
  public static class Builder {
    private final List<MenuButton> menuList;
    private int selectedId = NONE;
    private final Font normalFont;
    private final Font selectedFont;
    private final int width;
    private final int spacing;
    private int y;
    private int x;
    
    private Builder(int x, int y, int width, int spacing, Font normalFont, Font selectedFont) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.spacing = spacing;
      this.normalFont = normalFont;
      this.selectedFont = selectedFont;
      menuList = new ArrayList<MenuButton>();
      selectedId = NONE;
    }
    
    public Builder addItem(String item, EventType startEvent, EventType stopEvent,
        Font normalFont) {
      return addItem(item, startEvent, stopEvent, 0, 0, normalFont, selectedFont);
    }

    public Builder addItem(String item, EventType startEvent, EventType stopEvent) {
      return addItem(item, startEvent, stopEvent, 0, 0, normalFont, selectedFont);
    }

    public Builder addItem(String item, EventType startEvent, EventType stopEvent, int yPad) {
      return addItem(item, startEvent, stopEvent, yPad, 0, normalFont, selectedFont);
    }
   
    public Builder addItem(String item, EventType startEvent, EventType stopEvent, int yPad,
        int xPad, Font normalFont, Font selectedFont) {
      y += yPad;
      x += xPad;
      MenuButton option =
          new MenuButton(x, y, width, item, normalFont, selectedFont, startEvent, stopEvent);
      menuList.add(option);
      y += option.getHeight() + spacing;
      return this;
    }
        
    public Builder setSelectedId(int id) {
      menuList.get(id).select();
      selectedId = id;
      return this;
    }
    
    public Options build() {
      return new Options(menuList, selectedId);
    }
  }
  
  
  private Options(List<MenuButton> menuList, int selectedId) {
    this.menuList = menuList;
    this.selectedId = selectedId;
    
    // Build the list of interactables.
    this.interactableList = new ArrayList<Interactable>();
    for (MenuButton option : menuList) {
      interactableList.add(option);
    }
  }

  private void selectSetup() {
    assert(menuList.size() > 0);

    if (selectedId != NONE) {
      menuList.get(selectedId).unSelect();      
    }
  }
  
  public Options selectNext() {
    selectSetup();
    selectedId = selectedId + 1 >= menuList.size() ? 0 : selectedId + 1;
    menuList.get(selectedId).select();
    return this;
  }
  
  public Options selectPrevious() {
    selectSetup();
    selectedId = selectedId - 1 < 0 ? menuList.size() - 1 : selectedId - 1;
    menuList.get(selectedId).select();
    return this;
  }
  
  public Options setSelectedId(int id) {
    assert(id >= 0 && id < menuList.size());
    if (selectedId != NONE) {
      menuList.get(selectedId).unSelect();
    }
    menuList.get(id).select();
    selectedId = id;
    return this;
  }
  
  public int getSelectedId() {
    return selectedId;
  }
  
  // Hack to get around not having functional list transforms. :(  Android needs Gauva!
  public void addRenderables(List<Renderable> renderQueue) {
    for (MenuButton option : menuList) {
      renderQueue.add(option.getText());
    }
  }
  
  public List<Interactable> getInteractables() {
    return interactableList;
  }
  
  public static Builder newBuilder(int x, int y, int width, int spacing, Font normalFont,
      Font selectedFont) {
    return new Builder(x, y, width, spacing, normalFont, selectedFont);
  }
}
