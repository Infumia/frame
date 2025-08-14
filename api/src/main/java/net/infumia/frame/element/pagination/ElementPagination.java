package net.infumia.frame.element.pagination;

import net.infumia.frame.element.Element;
import net.infumia.frame.element.ElementContainer;

public interface ElementPagination extends Element, ElementContainer {
    char layout();

    int currentPageIndex();

    int nextPageIndex();

    int previousPageIndex();

    int lastPageIndex();

    boolean isFirstPage();

    boolean isLastPage();

    int elementCount();

    int pageCount();

    boolean hasPage(int pageIndex);

    void switchTo(int pageIndex);

    void advance();

    boolean canAdvance();

    void back();

    boolean canBack();
}
