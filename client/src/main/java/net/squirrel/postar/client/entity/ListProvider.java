package net.squirrel.postar.client.entity;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root
public class ListProvider {

    private List<Provider> providers;

    @ElementList(inline = true)
    public List<Provider> getProviders() {
        return providers;
    }

    @ElementList(inline = true)
    public void setProviders(List<Provider> providers) {
        this.providers = providers;
    }
}
