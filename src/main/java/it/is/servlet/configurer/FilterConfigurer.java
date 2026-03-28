package it.is.servlet.configurer;

import jakarta.servlet.Filter;
import org.apache.catalina.Context;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;

public class FilterConfigurer {

    public static void configureFilter(Class<? extends Filter> filterClass, Context context, String urlFilteringPattern) {
        FilterDef filterInfo = new FilterDef();
        filterInfo.setFilterClass(filterClass.getName());
        filterInfo.setFilterName(filterClass.getSimpleName());

        context.addFilterDef(filterInfo);

        FilterMap mapping = new FilterMap();
        mapping.setFilterName(filterClass.getSimpleName());
        mapping.addURLPattern(urlFilteringPattern);

        context.addFilterMap(mapping);
    }

}
