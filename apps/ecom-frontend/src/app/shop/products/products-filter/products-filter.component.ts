import { Component, effect, inject, input, output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FilterProductsFormContent, ProductFilter, ProductFilterForm, sizes } from '../../../admin/model/product.model';
import { FormBuilder, FormControl, FormRecord, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'ecom-products-filter',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './products-filter.component.html',
  styleUrl: './products-filter.component.scss',
})
export class ProductsFilterComponent {
  sort = input<string>('createdDate,asc');
  size = input<string>();

  productFilter = output<ProductFilter>();
  formBuilder = inject(FormBuilder);

  constructor() {
    effect(() => this.updateSizeFormValue());
    effect(() => this.updateSortFormValue());
    this.formFilterProducts.valueChanges.subscribe(() =>
      this.onFilterChange(this.formFilterProducts.getRawValue())
    );
  }

  formFilterProducts =
    this.formBuilder.nonNullable.group<FilterProductsFormContent>({
      // split(',')[1] returns asc   (in 'createdDate,asc')
      sort: new FormControl<string>(this.sort().split(',')[1], {
        nonNullable: true,
        validators: [Validators.required],
      }),
      size: this.buildSizeFormControl(),
    });

  private buildSizeFormControl(): FormRecord<FormControl<boolean>> {
    const sizeFormControl = this.formBuilder.nonNullable.record<
      FormControl<boolean>
    >({});
    for (const size of sizes) {
      sizeFormControl.addControl(
        size,
        new FormControl<boolean>(false, { nonNullable: true })
      );
    }
    return sizeFormControl;
  }

  // The goal is to process and update the status of products filters
  private onFilterChange(filter: Partial<ProductFilterForm>) {
    const filterProduct: ProductFilter = {
      size: '',
      sort: [`createdDate,${filter.sort}`],
    };

    let sizes: [string, boolean][] = [];
    if (filter.size !== undefined) {
      // Object.entries are use for convert { XS: true, M: false, etc... } to [['XS', true], ['M', false], etc...]
      sizes = Object.entries(filter.size);
    }

    for (const [sizeKey, sizeValue] of sizes) {
      if (sizeValue) {
        if (filterProduct.size?.length === 0) {
          filterProduct.size = sizeKey;
        } else {
          filterProduct.size += `,${sizeKey}`;
        }
      }
    }
    this.productFilter.emit(filterProduct);
  }

  public getSizeFormGroup(): FormRecord<FormControl<boolean>> {
    return this.formFilterProducts.get('size') as FormRecord<
      FormControl<boolean>
    >;
  }

  private updateSizeFormValue() {
    if (this.size()) {
      const sizes = this.size()!.split(',');
      for (const size of sizes) {
        // emitEvent is false because it triggers a new onFilterChange(...) which is useless
        // and in this.formFilterProducts.valueChanges subscribe to any value change
        this.getSizeFormGroup().get(size)!.setValue(true, { emitEvent: false });
      }
    }
  }

  // This updates the values, but not really order
  private updateSortFormValue() {
    if (this.sort()) {
      this.formFilterProducts.controls.sort.setValue(
        this.sort().split(',')[1],
        { emitEvent: false }
      );
    }
  }

  protected readonly sizes = sizes;
}
