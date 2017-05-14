package city.commerce.model;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-02
 */
public class Permission {
    public static final long User = 0x00000000;
    public static final long Super = 0XFFFFFFFF;
    public static final long Buyer = 0X000000001;
    public static final long Vendor = 0X00000002;
    public static final long Admin = 0X00000002;

    private long permissions;

    public Permission(long permissions) {
        this.permissions = permissions;
    }

    public long getPermissions() {
        return permissions;
    }

    public boolean has(long permissions) {
        return (this.permissions & permissions) == permissions;
    }

    public boolean has(long... permissions) {
        long perms = permissions[0];
        for (int i=1; i<permissions.length; i++) {
            perms = (perms | permissions[i]);
        }
        return has(perms);
    }
}
