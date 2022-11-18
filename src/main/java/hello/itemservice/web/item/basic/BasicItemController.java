package hello.itemservice.web.item.basic;


import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;



    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items",items);
        return "basic/items";
    }

    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

//    @PostMapping("/add")
//    public String addItemV1(
//            @RequestParam String itemName,
//            @RequestParam int price,
//            @RequestParam Integer quantity,
//            Model model
//    ){
//        Item item = new Item();
//        item.setItemName(itemName);
//        item.setPrice(price);
//        item.setQuantity(quantity);
//
//        itemRepository.save(item);
//
//        model.addAttribute("item",item);
//
//        return "basic/item";
//    }

    //@ModelAttribute는 Item 객체를 생성하고 요청 파라미터의 값을 프로퍼티 접근법(setXxx)으로 입력해준다ㅣ
    //@ModelAttribute는 중요한 한가지 기능이 더 있는데 바로 모델(Model)에 @ModelAttribute로
    //지정한 객체를 자동으로 넣어준디ModelAttribute model.addAttribute("item", item)이게 동작함
   //@ModelAttribute("이름") 이름으로 지정하고 뷰에서 렌더링 ㅇㅇ
//    @PostMapping("/add")
//    public String addItemV2(@ModelAttribute("item") Item item){
//        itemRepository.save(item);
//        log.info("##");
//        return "basic/item";
//    }

//    @PostMapping("/add")
//    public String addItemV4(Item item){
//        itemRepository.save(item);
//        log.info("#d#");
//        return "basic/item";
//    }

    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";
    }


    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId,Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);

        return "basic/item";
    }



    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId,@ModelAttribute Item item){
        itemRepository.update(itemId,item);
        return "redirect:basic/items/{itemId}";
    }


}
