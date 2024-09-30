package net.infumia.inv.api.element.pagination;

import net.infumia.inv.api.element.Element;
import net.infumia.inv.api.element.ElementContainer;

public interface ElementPagination extends Element, ElementContainer {
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
