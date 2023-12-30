package com.generic.khatabook.service.mapper;

import com.generic.khatabook.entity.Amount;
import com.generic.khatabook.model.AmountDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
class AmountMapperTest {


    @Test
    void testAmountDto() throws IOException {
        Assertions.assertThat(AmountMapper.dto(Amount.of(1000))).isInstanceOf(AmountDTO.class);
        Assertions.assertThat(AmountMapper.dto(Amount.of(1000))).isEqualTo(AmountDTO.of(1000));
        Assertions.assertThat(AmountMapper.dto(Amount.of(1000)).unitOfMeasurement()).isEqualTo(AmountDTO.of(1000).unitOfMeasurement());
        Assertions.assertThat(AmountMapper.dto(Amount.of(1000)).currency()).isEqualTo(AmountDTO.of(1000).currency());
    }

    @Test
    void testAmountAdd() throws IOException {
        Assertions.assertThat(AmountMapper.add(Amount.of(1000), Amount.of(1000))).isEqualTo(Amount.of(2000));
        Assertions.assertThat(AmountMapper.add(AmountDTO.of(1000), AmountDTO.of(1000))).isEqualTo(AmountDTO.of(2000));
        Assertions.assertThat(AmountMapper.add(AmountDTO.of(100), AmountDTO.of(500))).isEqualTo(AmountDTO.of(600));
        Assertions.assertThat(AmountMapper.add(AmountDTO.of(-100), AmountDTO.of(600))).isEqualTo(AmountDTO.of(500));
        Assertions.assertThat(AmountMapper.add(AmountDTO.of(-0), AmountDTO.of(0))).isEqualTo(AmountDTO.ZERO);
    }
}