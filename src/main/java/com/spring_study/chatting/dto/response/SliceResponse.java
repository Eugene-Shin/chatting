package com.spring_study.chatting.dto.response;

import org.springframework.data.domain.Slice;

import javax.naming.ldap.SortResponseControl;
import java.util.List;

public record SliceResponse<T>(
        List<T> content,
        Integer currentPage,
        Integer size,
        Boolean first,
        Boolean Last
) {
    public static <T> SliceResponse<T> from(Slice<?> slice,List<T> content){
        return new SliceResponse<>(
                content,
                slice.getNumber(),
                slice.getSize(),
                slice.isFirst(),
                slice.isLast()
        );
    }
}
