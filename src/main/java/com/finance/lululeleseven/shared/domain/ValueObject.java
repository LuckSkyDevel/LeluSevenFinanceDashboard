package com.finance.lululeleseven.shared.domain;

import java.util.Objects;

public abstract class ValueObject<T> {

    protected abstract T valor();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValueObject<?> that = (ValueObject<?>) o;
        return Objects.equals(valor(), that.valor());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(valor());
    }

    @Override
    public String toString() {
        return valor().toString();
    }
}
