package org.sergei.reservation.rest.dto.mappers;

import java.util.List;
import java.util.function.Function;

import com.google.common.collect.ImmutableList;

public interface IMapper<FROM,TO> extends Function<FROM, TO>{

    default List<TO> applyList(List<FROM> fromList) {
        return fromList.stream().map(this).collect(ImmutableList.toImmutableList());
    }
}
