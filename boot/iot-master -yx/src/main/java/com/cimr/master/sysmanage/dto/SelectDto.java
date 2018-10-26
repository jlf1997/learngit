package com.cimr.sysmanage.dto;

/***
 * @author pxh
 * @date 2017/12/20 11:44
 * @TODO 带搜索的select返回值对象
 */
public class SelectDto<K, V> {

    /**
     * option text
     */
    private K key;
    /**
     * option value
     */
    private V value;

    private String attribute;

    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public SelectDto(K key, V value, String attribute) {
        this.key = key;
        this.value = value;
        this.attribute = attribute;
    }

    public SelectDto(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public SelectDto() {
    }
}
