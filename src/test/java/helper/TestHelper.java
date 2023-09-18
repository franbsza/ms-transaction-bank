package helper;

import com.digital.mstransactionbank.dtos.AccountDTO;
import com.digital.mstransactionbank.dtos.TransactionDTO;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

public class TestHelper {

    public static AccountDTO mockAccountDTO() {
        return new EasyRandom(new EasyRandomParameters()).nextObject(AccountDTO.class);
    }

    public static TransactionDTO mockTransactionDTO() {
        return new EasyRandom(new EasyRandomParameters()).nextObject(TransactionDTO.class);
    }
}
