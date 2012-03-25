package jnr.posix;

import jnr.ffi.*;
import jnr.ffi.mapper.FromNativeContext;


final class MacOSPOSIX extends BaseNativePOSIX {
    final MacOSStatLayout statLayout;

    MacOSPOSIX(String libraryName, LibCProvider libcProvider, POSIXHandler handler) {
        super(libraryName, libcProvider, handler);
        statLayout = new MacOSStatLayout(getRuntime());
    }

    public FileStat allocateStat() {
        return new MacOSFileStat(this);
    }
    
    @Override
    public int lchmod(String filename, int mode) {
        try {
            return libc().lchmod(filename, mode);
        } catch (UnsatisfiedLinkError ex) {
            handler.unimplementedError("lchmod");
            return -1;
        }
    }
    
    @Override
    public int lchown(String filename, int user, int group) {
        try {
            return super.lchown(filename, user, group);
        } catch (UnsatisfiedLinkError ex) {
            handler.unimplementedError("lchown");
            return -1;
        }
    }
    
    public static final PointerConverter PASSWD = new PointerConverter() {
        public Object fromNative(Object arg, FromNativeContext ctx) {
            return arg != null ? new MacOSPasswd((Pointer) arg) : null;
        }
    };
}
