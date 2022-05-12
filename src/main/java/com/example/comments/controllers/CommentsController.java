package com.example.comments.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.comments.entities.Comments;
import com.example.comments.forms.CommentsForm;
import com.example.comments.repositories.CommentsRepository;

@Controller   
public class CommentsController {
	
    private CommentsRepository commentsRepo;
 
    @Autowired
    public CommentsController(CommentsRepository commentsRepo){
        this.commentsRepo = commentsRepo;
    }
    
    // 一覧表示
    @RequestMapping("/comments")
    public String commentList(Model model) {
    	model.addAttribute("title", "発言一覧");
    	model.addAttribute("comments", commentsRepo.findAllByOrderByCreatedAtDesc());
    	model.addAttribute("main", "comment/commentList::main");
    	return "layout/default";
    }
    
    // 詳細表示
    @GetMapping("/comments/detail/{id}")    
    public String MessageDetail(
    		@PathVariable("id")  Integer id,
    		Model model) {
        model.addAttribute("title", "id:" + id + "の編集");
        model.addAttribute("comment", commentsRepo.findById(id).orElseThrow());
        model.addAttribute("main", "comment/commentDetail::main");
        return "layout/default";    
    }
    
    // 新規登録
    @RequestMapping("/comments/create")
    public String commentCreate(
    		@ModelAttribute CommentsForm commentsForm,
    		Model model) {
    	model.addAttribute("title", "新規発言を追加");
    	model.addAttribute("commentsForm", commentsForm);
    	model.addAttribute("main", "comment/commentCreate::main");
    	return "layout/default";
    }
    
    // 登録処理
    @RequestMapping("/create")    
    public String create(
        @Valid CommentsForm commentsForm,
        BindingResult bindingResult,
        Model model
    ) {
        // エラーがあればフォームに戻る
        if(bindingResult.hasErrors()){
            return commentCreate(commentsForm, model);
        }
        Comments comments = new Comments();
        comments.setName(commentsForm.getName());
        comments.setBody(commentsForm.getBody());
        commentsRepo.save(comments);
        return "redirect:/comments";
    }
    
    // 編集更新
    @RequestMapping("/comments/update/{id}")
    public String commentUpdate(
    		@PathVariable("id")  Integer id,
    		@ModelAttribute CommentsForm commentsForm,
    		Model model) {
    	Comments comment = commentsRepo.findById(id).orElseThrow();
		commentsForm.setName(comment.getName());
		commentsForm.setBody(comment.getBody());
		
	    model.addAttribute("comment", comment);
    	model.addAttribute("title", "id :" + id + "の発言");
    	model.addAttribute("commentsForm", commentsForm);
    	model.addAttribute("main", "comment/commentUpdate::main");
    	return "layout/default";
    }
    
	 // 更新処理
    @PostMapping("/update/{id}")
    public String update(
    	@PathVariable("id")  Integer id,
        @Valid CommentsForm commentsForm,
        BindingResult bindingResult,
        Model model
    ) {
    	// エラーがあればフォームに戻る
	    if(bindingResult.hasErrors()){
	        return commentUpdate(id, commentsForm, model);
	    }
	    Comments comment = commentsRepo.findById(id).orElseThrow();
	    comment.setName(commentsForm.getName());
	    comment.setBody(commentsForm.getBody());
	    commentsRepo.save(comment);
	    return "redirect:/comments/detail/{id}";
    }
    
    // 削除
    @RequestMapping("/comments/delete/{id}")
    public String commentDelete(
    		@PathVariable("id")  Integer id,
    		Model model) {
    	commentsRepo.deleteById(id);
    	return "redirect:/comments";
    }
}
