import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateTournoiModalComponent } from './create-tournoi-modal.component';

describe('CreateTournoiModalComponent', () => {
  let component: CreateTournoiModalComponent;
  let fixture: ComponentFixture<CreateTournoiModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateTournoiModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateTournoiModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
