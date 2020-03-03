package lotto.domain.lotto;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import lotto.domain.number.LottoNumber;

class LottoTest {
	@DisplayName("lotto 생성자에 number List 입력이 들어올 때 객체 생성")
	@Test
	void constructor_NumberList_CreateLotto() {
		Set<LottoNumber> lottoNumbers = new HashSet<>(Arrays.asList(
			LottoNumber.valueOf(1),
			LottoNumber.valueOf(2),
			LottoNumber.valueOf(3),
			LottoNumber.valueOf(4),
			LottoNumber.valueOf(5),
			LottoNumber.valueOf(6)
		));
		assertThat(new Lotto(lottoNumbers)).isInstanceOf(Lotto.class);
	}

	@DisplayName("생성자에 null 입력이 들어올 때 InvalidLottoException 발생")
	@ParameterizedTest
	@NullSource
	void validateNull_NullNumberList_ExceptionThrown(Set<LottoNumber> lottoNumbers) {
		assertThatThrownBy(() -> new Lotto(lottoNumbers))
			.isInstanceOf(InvalidLottoException.class)
			.hasMessage(InvalidLottoException.NULL);
	}

	@DisplayName("생성자에 사이즈가 올바르지 않은 List 입력이 들어올 때 InvalidLottoException 발생")
	@Test
	void validateSize_NullNumberList_ExceptionThrown() {
		Set<LottoNumber> lottoNumbers = new HashSet<>(5);
		assertThatThrownBy(() -> new Lotto(lottoNumbers))
			.isInstanceOf(InvalidLottoException.class)
			.hasMessage(InvalidLottoException.WRONG_SIZE);
	}
}