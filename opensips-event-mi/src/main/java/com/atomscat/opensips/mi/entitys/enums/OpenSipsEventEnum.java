package com.atomscat.opensips.mi.entitys.enums;

/**
 * @author : zhouhailin
 * @version 1.0.0
 */
public enum OpenSipsEventEnum {

    E_UL_CONTACT_INSERT("E_UL_CONTACT_INSERT", "当在任何现有 AOR 的联系人列表中插入新联系人时，将引发此事件。 对于每个新联系人，如果其 AOR 在内存中不存在，则 E_UL_AOR_CREATE 和 E_UL_CONTACT_INSERT 事件都将引发。"),
    E_UL_CONTACT_DELETE("E_UL_CONTACT_DELETE", "当从现有 AOR 的联系人列表中删除联系人时，将引发此事件。 如果联系人是列表中唯一的联系人，则 E_UL_AOR_DELETE 和 E_UL_CONTACT_DELETE 事件都将引发。"),
    E_UL_CONTACT_UPDATE("E_UL_CONTACT_UPDATE", "当通过接收另一条注册消息更新联系人信息时，将引发此事件。"),
    /**
     * hash_entry - 对话表中的条目。这与hash_id一起用于唯一标识对话框。
     * hash_id - 对话表中的 id。这与hash_entry一起用于唯一标识对话框。
     * callid - callid。
     * from_tag - 发件人标签。
     * to_tag - To 标签。
     * old_state - 对话的旧状态。
     * new_state - 对话框的新状态。
     */
    E_DLG_STATE_CHANGED("E_DLG_STATE_CHANGED", "当对话框状态改变时引发此事件。");

    private String event;

    private String desc;

    OpenSipsEventEnum(String event, String desc) {
        this.event = event;
        this.desc = desc;
    }

    public String getEvent() {
        return event;
    }

    public String getDesc() {
        return desc;
    }
}
