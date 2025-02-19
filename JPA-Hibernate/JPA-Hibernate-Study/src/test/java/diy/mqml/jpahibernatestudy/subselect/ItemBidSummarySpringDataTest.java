/*
 * ========================================================================
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ========================================================================
 */
package diy.mqml.jpahibernatestudy.subselect;


import diy.mqml.jpahibernatestudy.persistence.entity.subselect.Bid;
import diy.mqml.jpahibernatestudy.persistence.entity.subselect.Item;
import diy.mqml.jpahibernatestudy.persistence.entity.subselect.ItemBidSummary;
import diy.mqml.jpahibernatestudy.persistence.entity.subselect.repositories.BidRepository;
import diy.mqml.jpahibernatestudy.persistence.entity.subselect.repositories.ItemBidSummaryRepository;
import diy.mqml.jpahibernatestudy.persistence.entity.subselect.repositories.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ItemBidSummarySpringDataTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private ItemBidSummaryRepository itemBidSummaryRepository;

    @Test
    public void itemBidSummaryTest() {

        Item item = new Item();
        item.setName("Some Item");
        item.setAuctionEnd(Helper.tomorrow());

        Item savedItem = itemRepository.save(item);

        Bid bid1 = new Bid(new BigDecimal(1000.0), item);
        Bid bid2 = new Bid(new BigDecimal(1100.0), item);


        bidRepository.save(bid1);
        bidRepository.save(bid2);

        Optional<ItemBidSummary> itemBidSummary = itemBidSummaryRepository.findByItemId(savedItem.getId()).stream().findFirst();

        assertAll(
                () -> assertEquals("Some Item", itemBidSummary.get().getName()),
                () -> assertEquals(2, itemBidSummary.get().getNumberOfBids())
        );

    }
}