package lotto.service;

import lotto.dao.DBConnection;
import lotto.dao.JdbcTemplate;
import lotto.dao.LottoRoundDao;
import lotto.dao.LottoTicketDao;
import lotto.domain.LottoMachine;
import lotto.domain.LottoQuantity;
import lotto.domain.lotto.LottoTicketGroup;
import lotto.domain.purchaseamount.PurchaseAmount;
import lotto.dto.PurchaseInformationDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LottoPurchaseService {
    private static LottoPurchaseService instance;

    private final LottoTicketDao lottoTicketDAO;
    private final LottoRoundDao lottoRoundDAO;

    private LottoPurchaseService() {
        JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance(DBConnection.getConnection());
        lottoRoundDAO = LottoRoundDao.getInstance(jdbcTemplate);
        lottoTicketDAO = LottoTicketDao.getInstance(jdbcTemplate);
    }

    public static LottoPurchaseService getInstance() {
        if (instance == null) {
            instance = new LottoPurchaseService();
        }
        return instance;
    }

    public PurchaseInformationDTO purchaseLottos(int round, String purchaseAmountText, String[] manualNums) throws SQLException {
        List<String> manualNumberText = (manualNums == null) ? new ArrayList<>() : Arrays.asList(manualNums);
        PurchaseAmount purchaseAmount = PurchaseAmount.createLottoPurchaseAmount(purchaseAmountText);

        LottoQuantity manualQuantity = LottoQuantity.create(manualNumberText.size(), purchaseAmount);
        LottoQuantity autoQuantity = purchaseAmount.maxLottoQuantity().subtract(manualQuantity);

        LottoTicketGroup totalLottos = generateLottos(manualNumberText, autoQuantity);
        saveLottos(round, totalLottos);

        return new PurchaseInformationDTO(
                manualQuantity.getQuantity(),
                autoQuantity.getQuantity(),
                totalLottos,
                purchaseAmount.buy(totalLottos.price()).getMoney()
        );
    }

    private LottoTicketGroup generateLottos(List<String> manualNumberText, LottoQuantity autoQuantity) {
        LottoTicketGroup manualLottos = LottoMachine.generateManualLottos(manualNumberText);
        LottoTicketGroup autoLottos = LottoMachine.generateAutoLottos(autoQuantity);
        return manualLottos.combine(autoLottos);
    }

    private void saveLottos(int round, LottoTicketGroup lottos) throws SQLException {
        lottoRoundDAO.insertRound(round);
        lottoTicketDAO.insertLottoTickets(round, lottos);
    }
}