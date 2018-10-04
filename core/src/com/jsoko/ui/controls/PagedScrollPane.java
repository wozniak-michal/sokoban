package com.jsoko.ui.controls;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;

public class PagedScrollPane extends ScrollPane {

    private PageChangedHandler pageChangedHandler;

    private boolean wasPanDragFling = false;
    private Table content;
    private int currentPage = 1;

    public PagedScrollPane() {
        super(null);
        setup();
    }

    public PagedScrollPane(Skin skin) {
        super(null, skin);
        setup();
    }

    public PagedScrollPane(Skin skin, String styleName) {
        super(null, skin, styleName);
        setup();
    }

    public PagedScrollPane(Actor widget, ScrollPaneStyle style) {
        super(null, style);
        setup();
    }

    private void setup() {
        content = new Table();
        super.setWidget(content);
    }

    public void recreate() {
        this.setScrollX(0);
        this.setScrollY(0);
        this.content.clearChildren();
    }

    public void setPageChangedHandler(PageChangedHandler handler) {
        this.pageChangedHandler = handler;
    }

    public void addPage(Actor page) {
        content.add(page).fill();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (wasPanDragFling && !isPanning() && !isDragging() && !isFlinging()) {
            wasPanDragFling = false;
            scrollToNearestPage();
            pageChangedHandler.handle(currentPage);
        } else {
            if (isPanning() || isDragging() || isFlinging()) {
                wasPanDragFling = true;
            }
        }
    }

    @Override
    public void setWidth(float width) {
        super.setWidth(width);
        if (content != null) {
            for (Cell cell : content.getCells()) {
                cell.width(width);
            }
            content.invalidate();
        }
    }

    public void setPageSpacing(float pageSpacing) {
        if (content != null) {
            content.defaults().space(pageSpacing);
            for (Cell cell : content.getCells()) {
                cell.space(pageSpacing);
            }
            content.invalidate();
        }
    }

    private void scrollToNearestPage() {
        final float scrollX = getScrollX();
        final float maxX = getMaxX();

        if (scrollX >= maxX || scrollX <= 0)
            return;

        Array<Actor> pages = content.getChildren();
        float pageX = 0;
        float pageWidth;
        if (pages.size > 0) {
            for (Actor page : pages) {
                pageX = page.getX();
                pageWidth = page.getWidth();
                if (scrollX < (pageX + pageWidth * 0.5)) {
                    break;
                }
            }
            setScrollX(MathUtils.clamp(pageX, 0, maxX));
            currentPage = Math.round(getScrollX() / getWidth()) + 1;
        }
    }

    public interface PageChangedHandler {
        void handle(int page);
    }
}