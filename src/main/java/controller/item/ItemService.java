package controller.item;

import model.Item;

import java.util.List;

public interface ItemService {
    boolean addItem(Item item);

    boolean updateItem(Item item);

    boolean deleteItem(String code);

    Item searchItem(String code);

    List<Item> getItems();
}
