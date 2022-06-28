import java.util.List;
import java.util.Map;

public interface SearchEngine {
    List<PageEntry> search(String word);
    Map<String, List<PageEntry>> sort(Map<String, List<PageEntry>> sortableMap);
}
