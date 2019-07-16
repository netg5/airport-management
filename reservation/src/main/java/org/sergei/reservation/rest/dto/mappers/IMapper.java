package org.sergei.reservation.rest.dto.mappers;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;
import org.springframework.data.domain.Page;

public interface IMapper<FROM,TO> extends Function<FROM, TO>{

    default List<TO> applyList(List<FROM> fromList) {
        return fromList.stream().map(this).collect(ImmutableList.toImmutableList());
    }

//    default Page<TO> applyPage(Page<FROM> fromPage) {
//        List<FROM> listFromPage = fromPage.getContent();
//        return listFromPage.stream().map(this).collect(ImmutableList.toImmutableList());
//    }
}
