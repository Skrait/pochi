package Backup;

import lombok.Data;

import java.io.Serializable;

/**
 * Author Peekaboo
 * Date 2021/12/7 15:40
 */
@Data
public class SysRoleMenu implements Serializable {

    /**
     * 主键，自增
     */
    private Long id;

    /**
     * 角色编号
     */
    private Long roleId;

    /**
     * 菜单编号
     */
    private Long menuId;
}
