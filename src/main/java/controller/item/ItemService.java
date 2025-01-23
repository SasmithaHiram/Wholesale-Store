package controller.item;

import model.Item;

import java.util.List;

public interface ItemService {
    boolean addItem();
    boolean updateItem();
    boolean deleteItem();
    Item searchItem(String code);
    List<Item> getItems();
}
