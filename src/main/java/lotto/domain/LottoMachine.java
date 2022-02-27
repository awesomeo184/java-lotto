package lotto.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LottoMachine {

    private static final int LOTTO_NUMBER_START_INDEX = 0;
    private static final int LOTTO_NUMBER_END_INDEX = 6;
    private static final int LOTTO_NUMBER_LOWER_BOUND = 1;
    private static final int LOTTO_NUMBER_UPPER_BOUND = 46;

    private final List<LottoNumber> numbers;

    public LottoMachine() {
        numbers = new ArrayList<>();
        for (int i = LOTTO_NUMBER_LOWER_BOUND; i < LOTTO_NUMBER_UPPER_BOUND; i++) {
            numbers.add(new LottoNumber(i));
        }
    }

    public List<Lotto> makeLottoTickets(int count) {
        ArrayList<Lotto> lottoTickets = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            lottoTickets.add(makeLottoTicket());
        }
        return lottoTickets;
    }

    private Lotto makeLottoTicket() {
        Collections.shuffle(numbers);
        return new Lotto(numbers.subList(LOTTO_NUMBER_START_INDEX, LOTTO_NUMBER_END_INDEX));
    }
}
