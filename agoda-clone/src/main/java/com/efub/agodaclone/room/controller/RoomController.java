package com.efub.agodaclone.room.controller;

import com.efub.agodaclone.room.dto.RoomDetailResponseDto;
import com.efub.agodaclone.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDetailResponseDto> getRoomDetail(@PathVariable("roomId") Long roomId){
        RoomDetailResponseDto responseDto = roomService.getDetailedRoom(roomId);
        return ResponseEntity.ok(responseDto);
    }
}
