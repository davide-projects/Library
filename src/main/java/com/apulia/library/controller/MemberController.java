package com.apulia.library.controller;

import com.apulia.library.model.Member;
import com.apulia.library.service.MemberService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/members")
@SecurityRequirement(name = "basicAuth")
public class MemberController {

    private final MemberService membersService;

    public MemberController(MemberService membersService) {
        this.membersService = membersService;
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        return ResponseEntity.ok(membersService.getAllMembers());
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable int id) {
        return ResponseEntity.ok(membersService.getMemberById(id));
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Member> addMember(@Valid @RequestBody Member member) {
        Member created = membersService.addMember(member);
        return ResponseEntity.created(URI.create("/members/" + created.getId()))
                .body(created);
    }

    // UPDATE (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(
            @PathVariable int id,
            @Valid @RequestBody Member member) {

        Member updated = membersService.updateMember(id, member);
        return ResponseEntity.ok(updated);
    }

    // PATCH
    @PatchMapping("/{id}")
    public ResponseEntity<Member> patchMember(
            @PathVariable int id,
            @RequestBody Map<String, Object> updates) {

        Member patched = membersService.patchMember(id, updates);
        return ResponseEntity.ok(patched);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable int id) {
        membersService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    // SEARCH (firstName, lastName)
    @GetMapping("/search")
    public ResponseEntity<List<Member>> searchMembers(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String phone) {

        List<Member> results = membersService.search(firstName, lastName, city, phone);
        return ResponseEntity.ok(results);
    }

}
