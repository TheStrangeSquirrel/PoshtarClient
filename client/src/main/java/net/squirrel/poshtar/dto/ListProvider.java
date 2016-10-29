package net.squirrel.poshtar.dto;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root
public class ListProvider {
    private String versionProviders;
    private List<Provider> providers;

    public ListProvider() {
    }

    public ListProvider(String versionProviders, List<Provider> providers) {
        this.versionProviders = versionProviders;
        this.providers = providers;
    }

    @ElementList(inline = true)
    public List<Provider> getProviders() {
        return providers;
    }

    @ElementList(inline = true)
    public void setProviders(List<Provider> providers) {
        this.providers = providers;
    }

    @Element
    public String getVersionProviders() {
        return versionProviders;
    }

    @Element
    public void setVersionProviders(String versionProviders) {
        this.versionProviders = versionProviders;
    }
}
