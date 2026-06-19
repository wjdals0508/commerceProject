package VO;

import java.text.DecimalFormat;

public class Money {
    private final long amount;

    DecimalFormat df = new DecimalFormat("###,###");

    public Money(long amount) {
        if (amount < 0) throw new IllegalArgumentException("가격은 0보다 낮을 수 없습니다.");
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money other)) return false;
        return amount == other.amount;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(amount);
    }

    public long getAmount() {
        return amount;
    }

    public String getAmountString() {
        return df.format(amount);
    }

    public Money add(Money other) {
        return new Money(this.amount + other.amount);
    }

    public Money sub(Money other) {
        return new Money(this.amount - other.amount);
    }

    public Money multi(Money other) {
        return new Money(this.amount * other.amount);
    }

    public Money multi(int other) {
        return new Money(this.amount * other);
    }

    public Money multi(double rate) {
        if (rate < 0) throw new IllegalArgumentException("배율은 0보다 낮을 수 없습니다.");

        return new Money((long)(this.amount * rate));
    }

    public boolean isOver(Money other) {
        return this.amount - other.amount > 0;
    }

    public boolean islessOrSame(Money other) {
        return this.amount - other.amount <= 0;
    }
}
