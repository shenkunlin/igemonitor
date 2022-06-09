package com.itheima.ige.rpc.client.job.fixed;
public class IgePage {

    //每页显示条数
    private Integer size=10;

    //当前页
    private Integer page;

    //总条数
    private Long total;

    //业务编号
    private String uniqueId;

    //命名空间
    private String namespace;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public Integer getSize() {
        return size;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
