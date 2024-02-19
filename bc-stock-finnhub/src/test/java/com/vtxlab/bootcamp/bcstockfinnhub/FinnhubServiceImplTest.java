package com.vtxlab.bootcamp.bcstockfinnhub;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import com.vtxlab.bootcamp.bcstockfinnhub.dto.jph.Quote;
import com.vtxlab.bootcamp.bcstockfinnhub.service.impl.FinnhubServiceImpl;

@ExtendWith(MockitoExtension.class)
public class FinnhubServiceImplTest {

  @InjectMocks
  private FinnhubServiceImpl finnhubServiceImpl;

  @Mock
  private RestTemplate restTemplate;

  // @Test
  void testGetQuote() {

    Quote expected = Quote.builder() //
                          .c(182.31) //
                          .d(-1.55) //
                          .dp(-0.843) //
                          .h(184.85) //
                          .l(181.665) //
                          .o(183.42) //
                          .pc(183.86) //
                          .t(1708117200) //
                          .build();
  //  Mockito.when(finnhubServiceImpl.)

                        //   {
                        //     "code": "000000",
                        //     "message": "OK.",
                        //     "data": {
                        //         "c": 182.31,
                        //         "d": -1.55,
                        //         "dp": -0.843,
                        //         "h": 184.85,
                        //         "l": 181.665,
                        //         "o": 183.42,
                        //         "pc": 183.86,
                        //         "t": 1708117200
                        //     }
                        // }

  }


}
