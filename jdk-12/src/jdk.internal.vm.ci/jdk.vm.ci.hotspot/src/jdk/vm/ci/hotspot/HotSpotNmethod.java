/*
 * Copyright (c) 2011, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
package jdk.vm.ci.hotspot;

import static jdk.vm.ci.hotspot.CompilerToVM.compilerToVM;
import jdk.vm.ci.code.InstalledCode;
import jdk.vm.ci.code.InvalidInstalledCodeException;
import jdk.vm.ci.meta.JavaKind;
import jdk.vm.ci.meta.JavaType;
import jdk.vm.ci.meta.ResolvedJavaMethod;

/**
 * Implementation of {@link InstalledCode} for code installed as an nmethod.
 *
 * When a {@link HotSpotNmethod} dies, it triggers unloading of the nmethod unless
 * {@link #isDefault() == true}.
 */
public class HotSpotNmethod extends HotSpotInstalledCode {

    /**
     * This (indirect) Method* reference is safe since class redefinition preserves all methods
     * associated with nmethods in the code cache.
     */
    private final HotSpotResolvedJavaMethod method;

    private final boolean isDefault;

    public HotSpotNmethod(HotSpotResolvedJavaMethod method, String name, boolean isDefault) {
        super(name);
        this.method = method;
        this.isDefault = isDefault;
    }

    /**
     * Determines if the nmethod associated with this object is the compiled entry point for
     * {@link #getMethod()}. If {@code false}, then the nmethod is unloaded when the VM determines
     * this object has died.
     */
    public boolean isDefault() {
        return isDefault;
    }

    public ResolvedJavaMethod getMethod() {
        return method;
    }

    @Override
    public void invalidate() {
        compilerToVM().invalidateInstalledCode(this);
    }

    @Override
    public String toString() {
        return String.format("InstalledNmethod[method=%s, codeBlob=0x%x, isDefault=%b, name=%s]", method, getAddress(), isDefault, name);
    }

    protected boolean checkThreeObjectArgs() {
        assert method.getSignature().getParameterCount(!method.isStatic()) == 3;
        assert method.getSignature().getParameterKind(0) == JavaKind.Object;
        assert method.getSignature().getParameterKind(1) == JavaKind.Object;
        assert !method.isStatic() || method.getSignature().getParameterKind(2) == JavaKind.Object;
        return true;
    }

    private boolean checkArgs(Object... args) {
        JavaType[] sig = method.toParameterTypes();
        assert args.length == sig.length : method.format("%H.%n(%p): expected ") + sig.length + " args, got " + args.length;
        for (int i = 0; i < sig.length; i++) {
            Object arg = args[i];
            if (arg == null) {
                assert sig[i].getJavaKind() == JavaKind.Object : method.format("%H.%n(%p): expected arg ") + i + " to be Object, not " + sig[i];
            } else if (sig[i].getJavaKind() != JavaKind.Object) {
                assert sig[i].getJavaKind().toBoxedJavaClass() == arg.getClass() : method.format("%H.%n(%p): expected arg ") + i + " to be " + sig[i] + ", not " + arg.getClass();
            }
        }
        return true;
    }

    @Override
    public Object executeVarargs(Object... args) throws InvalidInstalledCodeException {
        assert checkArgs(args);
        return compilerToVM().executeInstalledCode(args, this);
    }

    @Override
    public long getStart() {
        return isValid() ? super.getStart() : 0;
    }
}
