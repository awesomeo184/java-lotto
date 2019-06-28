package lotto.domain;

import lotto.domain.lotto.LottoTicket;
import lotto.domain.purchaseamount.PurchaseAmount;

import java.util.Objects;

public class LottoQuantity {
    public static final LottoQuantity ZERO = new LottoQuantity(0);
    private static final int MIN_QUANTITY = 0;

    private int quantity;

    private LottoQuantity(int quantity) {
        this.quantity = quantity;
        validateQuantity();
    }

    public static LottoQuantity create(int quantity) {
        if (quantity == MIN_QUANTITY) {
            return ZERO;
        }
        return new LottoQuantity(quantity);
    }

    public static LottoQuantity create(int quantity, PurchaseAmount purchaseAmount) {
        if (purchaseAmount.canBuy(quantity * LottoTicket.PRICE)) {
            return create(quantity);
        }
        throw new InvalidLottoQuantityException("생성 불가능한 로또 개수입니다.");
    }

    public static LottoQuantity create(String quantityText, PurchaseAmount purchaseAmount) {
        try {
            return create(Integer.parseInt(quantityText), purchaseAmount);
        } catch (NumberFormatException e) {
            throw new InvalidLottoQuantityException("로또 개수는 숫자만 가능합니다.");
        }
    }

    private void validateQuantity() {
        if (quantity < MIN_QUANTITY) {
            throw new InvalidLottoQuantityException(MIN_QUANTITY + "이상으로 설정 가능합니다.");
        }
    }

    public boolean biggerThan(int size) {
        return quantity > size;
    }

    public boolean biggerThan(LottoQuantity lottoQuantity) {
        return quantity > lottoQuantity.quantity;
    }

    public LottoQuantity subtract(LottoQuantity lottoQuantity) {
        return new LottoQuantity(this.quantity - lottoQuantity.quantity);
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LottoQuantity that = (LottoQuantity) o;
        return quantity == that.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity);
    }
}