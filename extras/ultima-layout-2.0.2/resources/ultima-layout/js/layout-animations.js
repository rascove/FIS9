/* JS extensions to support material animations */
if(PrimeFaces.widget.InputSwitch) {
    PrimeFaces.widget.InputSwitch = PrimeFaces.widget.InputSwitch.extend({
         
         init: function(cfg) {
             this._super(cfg);
             
             if(this.input.prop('checked')) {
                 this.jq.addClass('ui-inputswitch-checked');
             }
         },
         
         toggle: function() {
             var $this = this;

             if(this.input.prop('checked')) {
                 this.uncheck(); 
                 setTimeout(function() {
                    $this.jq.removeClass('ui-inputswitch-checked');
                 }, 100);
             }
             else {
                 this.check();
                 setTimeout(function() {
                    $this.jq.addClass('ui-inputswitch-checked');
                 }, 100);
             }
         }
    });
}

if(PrimeFaces.widget.SelectBooleanButton) {
    PrimeFaces.widget.SelectBooleanButton.prototype.check = function() {
        if(!this.disabled) {
            this.input.prop('checked', true);
            this.jq.addClass('ui-state-active').children('.ui-button-text').contents()[0].textContent = this.cfg.onLabel;

            if(this.icon.length > 0) {
                this.icon.removeClass(this.cfg.offIcon).addClass(this.cfg.onIcon);
            }

            this.input.trigger('change');
        }
    }
    
    PrimeFaces.widget.SelectBooleanButton.prototype.uncheck = function() {
        if(!this.disabled) {
            this.input.prop('checked', false);
            this.jq.removeClass('ui-state-active').children('.ui-button-text').contents()[0].textContent = this.cfg.offLabel;

            if(this.icon.length > 0) {
                this.icon.removeClass(this.cfg.onIcon).addClass(this.cfg.offIcon);
            }

            this.input.trigger('change');
        }
    }
}

PrimeFaces.skinInput = function(input) {
    setTimeout(function() {
        if(input.val() != '') {
            var parent = input.parent();
            input.addClass('ui-state-filled');
            
            if(parent.is("span:not('.md-inputfield')")) {
                parent.addClass('md-inputwrapper-filled');
            }
        }
    }, 1);
    
    input.on('mouseenter', function() {
        $(this).addClass('ui-state-hover');
    })
    .on('mouseleave', function() {
        $(this).removeClass('ui-state-hover');
    })
    .on('focus', function() {
        var parent = input.parent();
        $(this).addClass('ui-state-focus');
        
        if(parent.is("span:not('.md-inputfield')")) {
            parent.addClass('md-inputwrapper-focus');
        }
    })
    .on('blur', function() {
        $(this).removeClass('ui-state-focus');

        if(input.hasClass('hasDatepicker')) {
            setTimeout(function() {
                PrimeFaces.onInputBlur(input);
            },150);
        }
        else {
            PrimeFaces.onInputBlur(input);
        }
    });

    //aria
    input.attr('role', 'textbox')
            .attr('aria-disabled', input.is(':disabled'))
            .attr('aria-readonly', input.prop('readonly'));

    if(input.is('textarea')) {
        input.attr('aria-multiline', true);
    }

    return this;
};

PrimeFaces.onInputBlur = function(input) {
    var parent = input.parent(),
    hasInputFieldClass = parent.is("span:not('.md-inputfield')");
    
    if(parent.hasClass('md-inputwrapper-focus')) {
        parent.removeClass('md-inputwrapper-focus');
    }
    
    if(input.val() != '') {
        input.addClass('ui-state-filled');
        if(hasInputFieldClass) {
            parent.addClass('md-inputwrapper-filled');
        }
    }
    else {
        input.removeClass('ui-state-filled');
        parent.removeClass('md-inputwrapper-filled');
    }    
};

if(PrimeFaces.widget.AutoComplete) {
    PrimeFaces.widget.AutoComplete.prototype.setupMultipleMode = function() {
        var $this = this;
        this.multiItemContainer = this.jq.children('ul');
        this.inputContainer = this.multiItemContainer.children('.ui-autocomplete-input-token');
        
        if(this.hinput.children().length) {
            this.jq.addClass('md-inputwrapper-filled');
        }

        this.multiItemContainer.hover(function() {
                $(this).addClass('ui-state-hover');
            },
            function() {
                $(this).removeClass('ui-state-hover');
            }
        ).click(function() {
            $this.input.focus();
        });

        //delegate events to container
        this.input.focus(function() {
            $this.multiItemContainer.addClass('ui-state-focus');
            $this.jq.addClass('md-inputwrapper-focus');
        }).blur(function(e) {
            $this.multiItemContainer.removeClass('ui-state-focus');
            $this.jq.removeClass('md-inputwrapper-focus').addClass('md-inputwrapper-filled');
            
            setTimeout(function() {
                if($this.hinput.children().length == 0 && !$this.multiItemContainer.hasClass('ui-state-focus')) {
                    $this.jq.removeClass('md-inputwrapper-filled');
                }
            }, 150); 
        });

        var closeSelector = '> li.ui-autocomplete-token > .ui-autocomplete-token-icon';
        this.multiItemContainer.off('click', closeSelector).on('click', closeSelector, null, function(event) {
            if($this.multiItemContainer.children('li.ui-autocomplete-token').length === $this.cfg.selectLimit) {
                if(PrimeFaces.isIE(8)) {
                    $this.input.val('');
                }
                $this.input.css('display', 'inline');
                $this.enableDropdown();
            }
            $this.removeItem(event, $(this).parent());
        });
    };
};

if(PrimeFaces.widget.Calendar) {
    PrimeFaces.widget.Calendar.prototype.bindDateSelectListener = function() {
        var _self = this;

        this.cfg.onSelect = function() {
            if(_self.cfg.popup) {
                _self.fireDateSelectEvent();
            }
            else {
                var newDate = $.datepicker.formatDate(_self.cfg.dateFormat, _self.getDate());

                _self.input.val(newDate);
                _self.fireDateSelectEvent();
            }
            
            if(_self.input.val() != '') {
               var parent = _self.input.parent();
               parent.addClass('md-inputwrapper-filled');
               _self.input.addClass('ui-state-filled');
           }
        };
    };
}

if(PrimeFaces.widget.SelectOneMenu) {
    PrimeFaces.widget.SelectOneMenu = PrimeFaces.widget.SelectOneMenu.extend({
        init: function(cfg) {
            this._super(cfg);

            var $this = this;
            if(!this.disabled && this.jq.parent().hasClass('md-inputfield')) {
                this.itemsContainer.children('.ui-selectonemenu-item:first').css({'display': 'none'});
                if (this.input.val() != "") {
                    this.jq.addClass("ui-state-filled");
                }

                this.input.off('change').on('change', function() {
                    $this.inputValueControl($(this));
                });
                
                if(this.cfg.editable) {
                    this.label.on('input', function(e) {
                        $this.inputValueControl($(this));
                    }).on('focus', function() {
                        $this.jq.addClass('ui-state-focus');
                    }).on('blur', function() {
                        $this.jq.removeClass('ui-state-focus');
                        $this.inputValueControl($(this));
                    });
                }
            }
        },
        
        inputValueControl: function(input) {
            if (input.val() != "")
                this.jq.addClass("ui-state-filled"); 
            else
                this.jq.removeClass("ui-state-filled");
        }
    });
}
/* Ripple animation */
$(function() {     

    var ink, d, x, y;
    $(document.body).off('mousedown.ripple','.ripplelink,.ui-button,.ui-selectlistbox-item,.ui-multiselectlistbox-item,.ui-tabs-header,.ui-fieldset-toggler')
            .on('mousedown.ripple','.ripplelink,.ui-button,.ui-selectlistbox-item,.ui-multiselectlistbox-item,.ui-tabs-header,.ui-fieldset-toggler', null, function(e){
        var element = $(this);
        
        if(element.find(".ink").length === 0){
            if(element.hasClass('ripplelink'))
                element.children('span').after("<span class='ink'></span>");
            else
                element.append("<span class='ink'></span>");
        }
             
        ink = $(this).find(".ink");
        ink.removeClass("animate");
         
        if(!ink.height() && !ink.width()){
            d = Math.max($(this).outerWidth(), $(this).outerHeight());
            ink.css({height: d, width: d});
        }
         
        x = e.pageX - $(this).offset().left - ink.width()/2;
        y = e.pageY - $(this).offset().top - ink.height()/2;
         
        ink.css({top: y+'px', left: x+'px', 'pointer-events': 'none'}).addClass("animate");
    });
});