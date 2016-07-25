package net.squirrel.poshtar.dto;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root
public class ListProvider {

    private List<Provider> providers;

    public ListProvider() {
    }

    public ListProvider(List<Provider> providers) {
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
}
