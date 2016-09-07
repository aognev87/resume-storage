package ru.aognev.webapp.model;

import java.util.*;

/**
 * Created by aognev on 06.09.2016.
 */
public class OrganizationSection extends Section {
    //private final List<Organization> organizations;

    private final Map<Link, List<Organization>> organizationsMap = new HashMap<>();

    public OrganizationSection(List<Organization> organizations) {
        Objects.requireNonNull(organizations, "Organizations must not be null");

        for (Organization o : organizations) {
            List<Organization> innerList = organizationsMap.get(o.getHomePage());

            if (innerList != null) {
                innerList.add(o);
            } else {
                innerList = new ArrayList<>();
                innerList.add(o);
                organizationsMap.put(o.getHomePage(), innerList);
            }
        }
    }

    public Map<Link, List<Organization>> getOrganizations() {
        return organizationsMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationSection that = (OrganizationSection) o;

        return organizationsMap.equals(that.organizationsMap);

    }

    @Override
    public int hashCode() {
        return organizationsMap.hashCode();
    }

    @Override
    public String toString() {
        return organizationsMap.toString();
    }
}
