package fileprocessing.delegate;

import fileprocessing.service.filter.ProcessingFilter;
import fileprocessing.service.filter.impl.CSVFilterService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public enum FilterDelegate {
    FILTER_BY_COLUMN_2(new CSVFilterService( 2), FilterType.BASIC),
    FILTER_BY_COLUMN_3_4(new CSVFilterService(3, 4), FilterType.MULTIPLE_FIELDS);

    private ProcessingFilter processingFilter;
    private FilterType filterType;

    FilterDelegate(ProcessingFilter processingFilter, FilterType filterType) {
        this.processingFilter = processingFilter;
        this.filterType = filterType;
    }

    public static Set<FilterDelegate> getFilters(FilterType[] filterTypes) {

        Set<FilterDelegate> filters = new HashSet<>();
        for (FilterType filterType : filterTypes) {
            final Set<FilterDelegate> collect = Arrays.stream(FilterDelegate.values())
                    .filter(filter -> filter.getFilterType().equals(filterType))
                    .collect(Collectors.toSet());
            filters.addAll(collect);
        }

        return filters;

    }

    public ProcessingFilter getProcessingFilter() {
        return this.processingFilter;
    }

    public FilterType getFilterType() {
        return filterType;
    }
}
