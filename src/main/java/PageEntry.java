import java.util.Objects;

public class PageEntry implements Comparable<PageEntry> {
    private final String pdfName;
    private final int page;
    private final int count;

    public PageEntry(String pdfName, int page, int count) {
        this.pdfName = pdfName;
        this.page = page;
        this.count = count;
    }

    @Override
    public int compareTo(PageEntry o) {
        return o.count - count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PageEntry)) return false;
        PageEntry pageEntry = (PageEntry) o;
        return page == pageEntry.page && count == pageEntry.count && Objects.equals(pdfName, pageEntry.pdfName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pdfName, page, count);
    }

    @Override
    public String toString() {
        return "PageEntry{" +
                "pdf=" + pdfName +
                ", page=" + page +
                ", count=" + count +
                '}';
    }
}
