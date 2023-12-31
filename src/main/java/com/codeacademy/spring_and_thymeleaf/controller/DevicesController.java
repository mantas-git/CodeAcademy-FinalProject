package com.codeacademy.spring_and_thymeleaf.controller;

import com.codeacademy.spring_and_thymeleaf.model.Device;
import com.codeacademy.spring_and_thymeleaf.model.InfoMessage;
import com.codeacademy.spring_and_thymeleaf.model.User;
import com.codeacademy.spring_and_thymeleaf.service.DeviceService;
import com.codeacademy.spring_and_thymeleaf.utilities.FileUploadUtil;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@Controller
@RequestMapping("/devices")
public class DevicesController {
    private static final Logger logger = LoggerFactory.getLogger(ThymeleafController.class);
    private final DeviceService deviceService;

    public DevicesController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping
    public String showAllDevicesPageable(Device device, Model model,
                                         @AuthenticationPrincipal User user,
                                         @PageableDefault(sort = {"id"}, size = 15)
                                         Pageable pageable) {
        getAllDevices(user, pageable, model);
        return "devices";
    }

    @PostMapping("/add")
    public String addDevice(@Valid Device device,
                            BindingResult errors,
                            @AuthenticationPrincipal User user,
                            @PageableDefault(sort = {"id"}, size = 15) Pageable pageable,
                            RedirectAttributes redirectAttributes,
                            Model model) {
        logger.info("User {} adding new device: {}", user.getUsername(), device);
        if (!user.isVerified()) {
            InfoMessage infoMessage = new InfoMessage(true, "emailNotVerified");
            redirectAttributes.addFlashAttribute("infoMessage", infoMessage);
            return "redirect:/devices";
        }
        if (errors.hasErrors()) {
            getAllDevices(user, pageable, model);
            logger.info("Founded BindingResult errors: {}", errors);
            return "devices";
        }
        redirectAttributes.addFlashAttribute("infoMessage", deviceService.addDevice(device));
        logger.info("Device {} successfully added", device);
        return "redirect:/devices";

    }

    @GetMapping("/search")
    public String showFilteredDevices(@RequestParam String search,
                                      @AuthenticationPrincipal User user,
                                      @PageableDefault(sort = {"id"}, size = 15) Pageable pageable,
                                      Device device,
                                      Model model) {
        logger.info("Searching for {} in Device List", search);
        if (!search.isEmpty()) {
            Page<Device> devices = deviceService.findFilteredDevicesPaginated(user, pageable, search);
            logger.info("Found {} Devices", devices.getContent().size());
            pageableList(model, devices);
            return "devices";
        }
        return "redirect:/devices";
    }

    @RequestMapping(path = "/update", method = PUT, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public String updateDevice(@Valid Device device,
                               BindingResult errors,
                               @RequestParam("image") MultipartFile multipartFile,
                               @RequestParam("resetPhoto") Boolean resetPhoto,
                               RedirectAttributes redirectAttributes) throws IOException {
        logger.info("Updating Device ID {}", device.getId());
        if (errors.hasErrors()) {
            logger.info("Found BindingResult errors: {}", errors);
            return "devices";
        }
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        logger.info("Photo fileName: {}",  fileName);
        redirectAttributes.addFlashAttribute("infoMessage", deviceService.updateDevice(device, fileName, resetPhoto));
        logger.info("Device updated");
        if(!fileName.isEmpty()){
            String uploadDir = "src/main/resources/static/users-img/" + device.getDeviceId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            logger.info("Photo uploaded");
        }
        return "redirect:/devices";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteDevice(@PathVariable Long id) {
        logger.info("Trying delete Device with ID {}", id);
        deviceService.deleteDevice(id);
        logger.info("Device deleted");
        return "redirect:/devices";
    }

    private void getAllDevices(@AuthenticationPrincipal User user, @PageableDefault(size = 15) Pageable pageable, Model model) {
        logger.info("All Devices for User: {}", user);
        Page<Device> devices = deviceService.findPaginated(user, pageable);
        logger.info("Loaded Devices count: {}", devices.getContent().size());
        pageableList(model, devices);
    }

    private void pageableList(Model model, Page<Device> devices) {
        model.addAttribute("devices", devices);
        model.addAttribute("locale", LocaleContextHolder.getLocale());
        int totalPages = devices.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(0, totalPages - 1)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
    }
}
