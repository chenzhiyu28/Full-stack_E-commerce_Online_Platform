package com.leopold.store.controller;

import com.leopold.store.entity.District;
import com.leopold.store.service.IDistrictService;
import com.leopold.store.service.impl.DistrictServiceImpl;
import com.leopold.store.util.JsonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/districts")
public class DistrictController extends BaseController {
    private final IDistrictService districtService;

    public DistrictController(DistrictServiceImpl districtService) {
        this.districtService = districtService;
    }

    // 所有districts 开头的请求都会被 捕获
    @GetMapping({"/", ""}) // 等效于 @GetMapping("")
    public JsonResponse<List<District>> getDistrictsByParent(@RequestParam String parent) {
        List<District> districts = districtService.findDistrictByParent(parent);
        return successResponse(districts);
        // http://localhost:8080/districts/?parent=86
    }

}
