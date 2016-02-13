package cretion.core.entity.item;

import cretion.core.component.common.StatsComponent;
import cretion.core.entity.Entity;
import cretion.data.ItemData;

public class ItemEntity extends Entity {
    private ItemData itemData;

    public ItemEntity(ItemData _itemData) {
        itemData = _itemData;
        if (itemData.getType().equals(ItemData.EQUIPMENT)) {
            addComponent(new StatsComponent().setStrength(itemData.getValue("strength"))
                    .setDexterity(itemData.getValue("dexterity")).setIntelligence(itemData.getValue("intelligence"))
                    .setLuck(itemData.getValue("luck")));
        }
    }

    public ItemData getItemData() {
        return itemData;
    }
}
